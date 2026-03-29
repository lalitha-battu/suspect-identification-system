import cv2
import face_recognition
import numpy as np
import requests

# 🔹 Load suspects (simulate DB)
suspects_from_db = [
    {"id": 10, "name": "Ravi", "image_path": "uploads/ravi.jpg"},
    {"id": 11, "name": "Kiran", "image_path": "uploads/kiran.jpg"},
    {"id": 13, "name": "Lalli", "image_path": "uploads/lalli.jpg"}
]

# 🔹 Step 1: Encode known faces
known_encodings = []
known_names = []
known_ids = []

print("🔄 Loading known faces...")

for suspect in suspects_from_db:
    image = face_recognition.load_image_file(suspect["image_path"])
    encodings = face_recognition.face_encodings(image)

    print(f"Checking: {suspect['name']}")

    if len(encodings) > 0:
        print("✅ Face found")
        known_encodings.append(encodings[0])
        known_names.append(suspect["name"])
        known_ids.append(suspect["id"])
    else:
        print("❌ No face found in image")

print("✅ All faces loaded\n")

# 🔹 Step 2: Start camera
video = cv2.VideoCapture(0)

last_detected_id = None  # prevent repeat alerts

print("🎥 Camera started... Press 'q' to quit")

while True:
    ret, frame = video.read()
    if not ret:
        break

    rgb_frame = frame[:, :, ::-1]

    # 🔹 Step 3: Detect + Encode faces
    face_locations = face_recognition.face_locations(rgb_frame)
    face_encodings = face_recognition.face_encodings(rgb_frame, face_locations)

    for (top, right, bottom, left), face_encoding in zip(face_locations, face_encodings):

        name = "Unknown"
        suspect_id = None

        if len(known_encodings) > 0:
            face_distances = face_recognition.face_distance(known_encodings, face_encoding)
            best_match_index = np.argmin(face_distances)

            # 🔥 Threshold (important)
            if face_distances[best_match_index] < 0.5:
                name = known_names[best_match_index]
                suspect_id = known_ids[best_match_index]

                # 🔥 Avoid repeated alerts
                if suspect_id != last_detected_id:
                    print(f"🚨 MATCH FOUND: {name}")

                    try:
                        requests.post(
                            "http://localhost:8080/api/alerts/detect",
                            params={
                                "suspectId": suspect_id,
                                "userId": 1,
                                "location": "Gate1"
                            }
                        )
                    except:
                        print("⚠️ Backend not reachable")

                    last_detected_id = suspect_id
            else:
                print("❌ Unknown Face")

        # 🔹 Draw rectangle + name
        cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 0), 2)
        cv2.putText(frame, name, (left, top - 10),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 2)

    cv2.imshow("Camera", frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

video.release()
cv2.destroyAllWindows()

import cv2
import os

# Ask for user's name
name = input("Enter the name of the person: ").strip().lower().replace(" ", "_")

# Create directory for the user
save_dir = os.path.join("image_dir", name)
os.makedirs(save_dir, exist_ok=True)

# Load Haar cascade
face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + "haarcascade_frontalface_default.xml")

# Start webcam
cap = cv2.VideoCapture(0)

count = 0
print("Capturing face images. Press 'q' to quit early.")

while True:
    ret, frame = cap.read()
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    for (x, y, w, h) in faces:
        face = gray[y:y+h, x:x+w]
        face = cv2.resize(face, (200, 200))
        count += 1
        cv2.imwrite(os.path.join(save_dir, f"{count}.jpg"), face)
        cv2.rectangle(frame, (x, y), (x+w, y+h), (255, 0, 0), 2)

    cv2.imshow('Capturing Faces', frame)

    if cv2.waitKey(1) & 0xFF == ord('q') or count >= 30:
        break

cap.release()
cv2.destroyAllWindows()
print(f"Saved {count} images to {save_dir}")

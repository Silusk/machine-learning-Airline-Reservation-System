
import speech_recognition as sr

def listen_and_parse():
    r = sr.Recognizer()
    with sr.Microphone() as source:
        print("Speak a command:")
        audio = r.listen(source)
    try:
        text = r.recognize_google(audio)
        print("You said:", text)
        return text
    except sr.UnknownValueError:
        print("Google Speech Recognition could not understand audio")
        return "Could not understand"
    except sr.RequestError as e:
        print(f"Could not request results from Google; {e}")
        return "Service error"

if __name__ == "__main__":
    result = listen_and_parse()
    print("Result:", result)

import sys
import pandas as pd
import pickle

def predict_price(airline, source, destination):
    # Load model and columns
    with open('price_model.pkl', 'rb') as f:
        model, columns = pickle.load(f)

    # Prepare input
    input_data = pd.DataFrame([{
        'airline': airline,
        'source': source,
        'destination': destination
    }])

    # One-hot encode
    input_encoded = pd.get_dummies(input_data)
    for col in columns:
        if col not in input_encoded.columns:
            input_encoded[col] = 0
    input_encoded = input_encoded[columns]

    # Predict
    prediction = model.predict(input_encoded)
    return f"{prediction[0]:.2f}"

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print("Usage: python predict_price.py <airline> <source> <destination>")
        sys.exit(1)

    airline = sys.argv[1]
    source = sys.argv[2]
    destination = sys.argv[3]

    result = predict_price(airline, source, destination)
    print(result)

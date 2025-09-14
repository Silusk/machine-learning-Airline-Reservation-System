
import pandas as pd
import pickle
from sklearn.linear_model import LinearRegression

# Sample dataset
data=pd.read_csv("flight_data.csv")
# data = {
#     'airline': ['Air India', 'IndiGo', 'SpiceJet', 'Air India', 'IndiGo'],
#     'source': ['Delhi', 'Chennai', 'Delhi', 'Delhi', 'Chennai'],
#     'destination': ['Mumbai', 'Kolkata', 'Bangalore', 'Mumbai', 'Kolkata'],
#     'price': [4500, 4000, 5000, 4600, 3900]
# }

df = pd.DataFrame(data)

# Convert categorical columns to numeric
df_encoded = pd.get_dummies(df[['airline', 'source', 'destination']])
X = df_encoded
y = df['price']

# Train the model
model = LinearRegression()
model.fit(X, y)

# Save the model and columns
with open('price_model.pkl', 'wb') as f:
    pickle.dump((model, list(X.columns)), f)

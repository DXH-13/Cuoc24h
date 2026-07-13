# Security And Privacy Rules

## Sensitive Data

Treat these as sensitive:

- Customer phone number
- Pickup and dropoff addresses
- Driver phone number
- License plate
- Admin credentials
- Zalo/webhook credentials

## Secrets

- Never commit real secrets.
- Use environment variables for credentials.
- Provide `.env.example` style placeholders when configuration is needed.
- Do not hard-code tokens in frontend code.

## Public Surface

- Customers can create bookings without login, so validation and abuse protection matter.
- Do not expose full driver lists publicly.
- Do not expose admin notes publicly.
- Avoid leaking internal IDs where a public reference code is enough.

## Logs

- Logs should help debug failures without exposing too much personal data.
- Do not log raw passwords or tokens.
- Consider masking phone numbers in logs outside local development.


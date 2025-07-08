# PassPal - Personal Telegram Bot

PassPal is a simple, private Telegram bot built in Java with Spring Boot.

## Features

- `/add <name> <password>` - Add a new password
- `/pass <name>` - Retrieve password (supports partial name search)
- `/update <name> <new password>` - Update an existing password
- `/delete <name>` - Delete a password
- `/show` - List all stored passwords
- Passwords are encrypted before saving in database
- Auto-deletes bot messages after 2 minutes for security reasons
- Only the authorized user (by Telegram chatId) can interact
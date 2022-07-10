db.createUser({
  user: 'cafeteria-user',
  pwd: 'cafeteria-password',
  roles: [
    {
      role: 'readWrite',
      db: 'cafeteria',
    },
  ],
});

db.createCollection("users")

db.users.insert({"fullName": "name",
  "email": "teste@teste.com",
  "password": "password",
  "enabled": true});
db.createUser(
    {
        user: "alexlibrary",
        pwd: "alexlibrary",
        roles: [
            {
                role: "readWrite",
                db: "library"
            }
        ]
    }
);
return [
    subject: "Your username has been successfully created",
    body: """
Dear ${givenName ?: "user"},
Congratulations! Your username has been successfully created 🔒
Username: ${username}
You can now use your username instead of your email address to sign in, making your login experience smoother and more secure.
But that’s not all!
We’re not just upgrading how you log in, we’re setting the stage for something exciting. Powerful new features are on the way, designed to help boost your journey toward a more prosperous financial future.
Stay tuned, the best is yet to come!
In the meantime, if you have any questions or need support, we’re just a click away.
Kind regards,
Phi Wallet Team
"""
]

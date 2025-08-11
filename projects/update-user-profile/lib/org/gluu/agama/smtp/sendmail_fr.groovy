return [
    subject: "Votre nom d’utilisateur a été créé avec succès",
    body: """
Bonjour ${givenName ?: "utilisateur"},
Félicitation! Votre nom d’utilisateur a été créé avec succès 🔒
Nom d’utilisateur : ${username}
Vous pouvez désormais utiliser votre nom d’utilisateur au lieu de votre adresse e-mail pour vous connecter, offrant ainsi une expérience plus fluide et sécurisée.
Mais ce n’est pas tout !
Nous ne faisons pas que simplifier votre connexion, nous préparons aussi quelque chose d’excitant. De nouvelles fonctionnalités arriveront bientôt, conçues pour booster votre parcours vers un avenir financier plus prospère.
Restez à l’écoute, le meilleur est à venir !
Entre-temps, si vous avez des questions ou besoin d’assistance, nous sommes à un clic de distance.
Cordialement,
L’équipe Phi Wallet
"""
]

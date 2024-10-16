import { AbstractControl, ValidationErrors } from '@angular/forms';

// Fonction de validation personnalisée pour vérifier la complexité du mot de passe
export function passwordValidator(control: AbstractControl): ValidationErrors | null {
    const value = control.value;  // Récupère la valeur du champ de formulaire

    if (!value) {
        return null;  // Si la valeur est vide, retourne null (aucune erreur de validation)
    }

    // Vérifications pour s'assurer que le mot de passe respecte certaines règles de complexité
    const hasNumber = /[0-9]/.test(value);  // Vérifie la présence d'au moins un chiffre
    const hasUpperCase = /[A-Z]/.test(value);  // Vérifie la présence d'une lettre majuscule
    const hasLowerCase = /[a-z]/.test(value);  // Vérifie la présence d'une lettre minuscule
    const hasSpecialCharacter = /[!@#$%^&*(),.?":{}|<>]/.test(value);  // Vérifie la présence d'un caractère spécial
    const isValidLength = value.length >= 8;  // Vérifie que la longueur du mot de passe est d'au moins 8 caractères

    // Le mot de passe est considéré valide si toutes les conditions ci-dessus sont remplies
    const passwordValid = hasNumber && hasUpperCase && hasLowerCase && hasSpecialCharacter && isValidLength;

    if (!passwordValid) {
        // Si le mot de passe ne respecte pas ces règles, retourne une erreur de validation
        return { passwordStrength: true };
    }

    // Si toutes les conditions sont respectées, retourne null (aucune erreur de validation)
    return null;
}

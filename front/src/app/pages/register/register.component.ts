import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router'; // Importer le service Router pour la redirection
import { AuthService } from 'src/app/services/auth.service'; // Importer le service d'authentification
import { passwordValidator } from 'src/app/validators/password.validator'; // Importer le validateur personnalisé de mot de passe

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
    registerForm: FormGroup; // Déclaration du formulaire d'enregistrement

    // Le constructeur initialise les services requis : FormBuilder, AuthService, et Router
    constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
        // Création du formulaire avec des champs de validation
        this.registerForm = this.fb.group({
            username: ['', Validators.required], // Le nom d'utilisateur est obligatoire
            email: ['', [Validators.required, Validators.email]], // L'email est obligatoire et doit être valide
            password: ['', [Validators.required, passwordValidator]] // Le mot de passe est obligatoire et doit passer le validateur personnalisé
        });
    }

    // Méthode appelée lors de la soumission du formulaire
    onSubmit(): void {
        // Vérification si le formulaire est valide
        if (this.registerForm.valid) {
            // Appel du service d'authentification pour enregistrer l'utilisateur avec les valeurs du formulaire
            this.authService.register(this.registerForm.value).subscribe(
                (response) => {
                    // Si l'inscription réussit, afficher un message et rediriger l'utilisateur vers la page des articles
                    console.log('Registration successful:', response);
                    this.router.navigate(['/articles']); // Redirection après l'inscription
                },
                (error) => {
                    // Si l'inscription échoue, afficher un message d'erreur
                    console.error('Registration failed:', error);
                }
            );
        } else {
            // Si le formulaire est invalide, afficher un message d'erreur
            console.error('Form is invalid');
        }
    }
}

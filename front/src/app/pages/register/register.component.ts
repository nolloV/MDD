import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router'; // Importer le service Router
import { AuthService } from 'src/app/services/auth.service';
import { passwordValidator } from 'src/app/validators/password.validator'; // Importer le validateur de mot de passe

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
    registerForm: FormGroup;

    constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
        this.registerForm = this.fb.group({
            username: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, passwordValidator]] // Utiliser le validateur de mot de passe
        });
    }

    onSubmit(): void {
        if (this.registerForm.valid) {
            this.authService.register(this.registerForm.value).subscribe(
                (response) => {
                    console.log('Registration successful:', response);
                    this.router.navigate(['/articles']); // Rediriger vers la page des articles
                },
                (error) => {
                    console.error('Registration failed:', error);
                }
            );
        } else {
            console.error('Form is invalid');
        }
    }
}
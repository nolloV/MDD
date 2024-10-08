import { Component, OnInit } from '@angular/core';
import { ThemeService } from '../../services/theme.service';
import { Theme } from '../../models/theme';

@Component({
    selector: 'app-theme-list',
    templateUrl: './theme-list.component.html',
    //   styleUrls: ['./theme-list.component.css']
})
export class ThemeListComponent implements OnInit {
    themes: Theme[] = [];

    constructor(private themeService: ThemeService) { }

    ngOnInit(): void {
        this.loadThemes();
    }

    loadThemes(): void {
        this.themeService.getThemes().subscribe(themes => this.themes = themes);
    }

    addTheme(): void {
        const newTheme: Theme = {
            id: 0,
            title: 'Nouveau Thème',
            description: 'Description',
            createdAt: new Date(),
            updatedAt: new Date()
        };
        this.themeService.addTheme(newTheme).subscribe(theme => this.themes.push(theme));
    }

    updateTheme(theme: Theme): void {
        this.themeService.updateTheme(theme.id, theme).subscribe(updatedTheme => {
            const index = this.themes.findIndex(t => t.id === updatedTheme.id);
            if (index !== -1) {
                this.themes[index] = updatedTheme;
            }
        });
    }

    deleteTheme(id: number): void {
        this.themeService.deleteTheme(id).subscribe(() => {
            this.themes = this.themes.filter(theme => theme.id !== id);
        });
    }
}
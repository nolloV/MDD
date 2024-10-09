import { Component, OnInit } from '@angular/core';
import { ThemeService } from '../../services/theme.service';
import { Theme } from '../../models/theme';

@Component({
    selector: 'app-theme-list',
    templateUrl: './theme-list.component.html',
    styleUrls: ['./theme-list.component.scss']
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
}

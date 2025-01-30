// app.config.ts
import { ApplicationConfig, importProvidersFrom, APP_INITIALIZER, LOCALE_ID } from '@angular/core';
import { provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient, HTTP_INTERCEPTORS, withInterceptorsFromDi } from '@angular/common/http';
import { TokenInterceptor } from './helpers/token.interceptor';

// ngx-translate-Imports
import { TranslateModule, TranslateLoader, TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';

// 1) Loader-Funktion für ngx-translate
export function HttpLoaderFactory(http: HttpClient) {
  // Standard: Lädt Übersetzungsdateien aus 'assets/i18n/[lang].json'
  return new TranslateHttpLoader(http);
}

// 2) APP_INITIALIZER, um beim App-Start Sprachen zu konfigurieren
export function initApp(translate: TranslateService) {
  return () => {
    // Verfügbare Sprachen
    translate.addLangs(['en', 'de']);

    // Standard-/Fallback-Sprache
    translate.setDefaultLang('de');

    // Wenn du direkt 'de' verwenden willst:
    translate.use('de');
  };
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
    // Angular-weites Locale (optional)
    {
      provide: LOCALE_ID,
      useValue: 'de'
    },

    // 3) ngx-translate via 'importProvidersFrom'
    importProvidersFrom(
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
        }
      })
    ),

    // 4) APP_INITIALIZER registrieren, damit die Übersetzungen
    //    direkt beim Hochfahren der App initialisiert werden
    {
      provide: APP_INITIALIZER,
      useFactory: initApp,
      deps: [TranslateService],
      multi: true
    }
  ],
};

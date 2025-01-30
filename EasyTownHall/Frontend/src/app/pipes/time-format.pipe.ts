import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timeFormat',
  standalone: true
})
export class TimeFormatPipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return '';

    // Teile den Wert auf (z.B. "8,0" -> ["8", "0"])
    const parts = value.split(',');

    // Stelle sicher, dass wir zwei Teile haben und formatiere
    const hours = parts[0].padStart(2, '0'); // Immer 2-stellig
    const minutes = parts[1]?.padStart(2, '0') || '00'; // Minuten sicherstellen

    return `${hours}:${minutes} Uhr`; // Formatierte Zeit
  }
}

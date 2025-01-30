import { DatePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'articlefilter',
  standalone: true
})
export class ArticlefilterPipe implements PipeTransform {

  private datePipe = new DatePipe('de-DE');

  transform(value: any, args?: any): any {
    if (!value) return null;
    if (!args) return value;

    args = args.toLowerCase();

    return value.filter((item: any) => {
      // Datum formatieren
      const formattedDate = item.createdAt
        ? this.datePipe.transform(item.createdAt, 'dd. MMMM yyyy')
        : '';

      // Filter-Kriterium anwenden
      return (
        JSON.stringify(item).toLowerCase().includes(args) ||
        (formattedDate && formattedDate.toLowerCase().includes(args))
      );
    });
  }

}

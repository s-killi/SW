import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FootballStatsComponent } from './football-stats.component';

describe('FootballStatsComponent', () => {
  let component: FootballStatsComponent;
  let fixture: ComponentFixture<FootballStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FootballStatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FootballStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

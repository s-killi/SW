import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenFormListComponent } from './citizen-form-list.component';

describe('CitizenFormListComponent', () => {
  let component: CitizenFormListComponent;
  let fixture: ComponentFixture<CitizenFormListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CitizenFormListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenFormListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminFormListComponent } from './admin-form-list.component';

describe('AdminFormListComponent', () => {
  let component: AdminFormListComponent;
  let fixture: ComponentFixture<AdminFormListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminFormListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminFormListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

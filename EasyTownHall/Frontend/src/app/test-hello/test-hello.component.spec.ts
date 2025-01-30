import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestHelloComponent } from './test-hello.component';

describe('TestHelloComponent', () => {
  let component: TestHelloComponent;
  let fixture: ComponentFixture<TestHelloComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TestHelloComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestHelloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

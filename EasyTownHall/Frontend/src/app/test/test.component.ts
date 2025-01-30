import { CommonModule } from '@angular/common';
import { Component} from '@angular/core';
import { Router, RouterModule} from '@angular/router';
import { interval, Observable, ReplaySubject, Subject, switchMap, takeUntil } from 'rxjs';
import {ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { TestService } from '../services/test.service';
import { TestModel } from '../models/testModel';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [RouterModule, CommonModule, ReactiveFormsModule],
  templateUrl: './test.component.html',
  styleUrl: './test.component.scss'
})

export class TestComponent {
  
  constructor(private testService : TestService){}
  
  title = 'easyTownHall-frontend';
    
  //Observables werden mit $ geendet. ist convention
  message$: Observable<string> | undefined;
  testModel$: Observable<TestModel> | undefined;
  userById$: Observable<TestModel> | undefined;
  allUsers = new ReplaySubject<TestModel[]>(1);
  ngUnsubscribe$ = new Subject<void>(); // Ggf. signal verwenden. Es gibt mittlerweile ein destroy das man direkt injecten kann.
  
  profileForm = new FormGroup({    
    name: new FormControl('', Validators.required),    
    age: new FormControl(0, Validators.required),  
  });
  profileFormUpdate = new FormGroup({    
    nameUpdate: new FormControl('', Validators.required),    
    ageUpdate: new FormControl(0, Validators.required),  
  });
  
  ngOnInit(): void {
    this.message$ = this.testService.getHello();
    this.testModel$ = this.testService.getFirstUser();
    this.userById$ = this.testService.getUserById(2);
    this.updateAllUsers(this.testService.getAll());
  }
  
  handleSubmit() : void {
    if (this.profileForm.valid) {
      let user = this.checkProfileForm()
      this.updateAllUsers(this.testService.addUser(user));
    }
  }
  
  deleteUser(id: number){
    this.updateAllUsers(this.testService.deleteUser(id)); 
  }
  
  updateUser(id: number){
    if (this.profileFormUpdate.valid) {
    const user = this.checkProfileFormUpdate();
    this.updateAllUsers(this.testService.updateUser(id, user)); 
    }

    // nachdem ein user geupdatet wurde müssen die relateten Values wieder gelöscht werden
    // NUR FÜR TEST <- ANDERE MÖGLICHKEIT SUCHEN
    this.profileFormUpdate = new FormGroup({    
      nameUpdate: new FormControl('', Validators.required),    
      ageUpdate: new FormControl(0, Validators.required),  
    });
  }

  //Holen die Daten aus der Form
  checkProfileForm(): Partial<TestModel> {
      let user: Partial<TestModel> = {
        name: this.profileForm.value.name ?? '',
        age: this.profileForm.value.age ?? 0,
      };
      return user;
  }

  checkProfileFormUpdate(): Partial<TestModel> {
    let user: Partial<TestModel> = {
      name: this.profileFormUpdate.value.nameUpdate ?? '',
      age: this.profileFormUpdate.value.ageUpdate ?? 0,
    };
    return user;
  }

//Siehe Felder allUsers und ngUnsubscribe. Diese Methode 
  updateAllUsers<T>(observable: Observable<T>): void {
    observable.pipe(
      takeUntil(this.ngUnsubscribe$),
      switchMap(() => this.testService.getAll())
    ).subscribe(data => this.allUsers.next(data));
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe$.next();
    this.ngUnsubscribe$.complete();
  }
  
}
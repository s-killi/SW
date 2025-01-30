import { Component } from '@angular/core';
import { UserDTO } from '../models/DTOs/UserDTO';
import { Observable, ReplaySubject, Subject, switchMap, takeUntil } from 'rxjs';
import { UserService } from '../services/user.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-user-role-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TranslateModule],
  templateUrl: './user-role-form.component.html',
  styleUrl: './user-role-form.component.scss'
})
export class UserRoleFormComponent {
  allUsers = new ReplaySubject<UserDTO[]>(1);
  ngUnsubscribe$ = new Subject<void>();

  constructor(private userService: UserService, private fb: FormBuilder){}

  roleForms: { [userId: number]: FormGroup } = {}

  ngOnInit(): void {
    this.updateAllUsers(this.userService.getAll());
  
    this.allUsers.subscribe(users => {
      users.forEach(user => {
        // Initialisiere das Formular für jede Rolle des Benutzers
        this.roleForms[user.id] = this.fb.group({
          isRole: [user.roles.map(role => role.name)],  // Array aller Rollen des Benutzers
          roleSelection: [
            user.roles.some(role => role.name === 'SERVICEWORKER') ? 'SERVICEWORKER' :
            user.roles.some(role => role.name === 'ADMIN') ? 'ADMIN' : 'USER'  // Standardwert basierend auf vorhandenen Rollen
          ]
        });
      });
    });
  }

  updateAllUsers<T>(observable: Observable<T>): void {
    observable.pipe(
      takeUntil(this.ngUnsubscribe$),
      switchMap(() => this.userService.getAll())
    ).subscribe(data => this.allUsers.next(data));
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe$.next();
    this.ngUnsubscribe$.complete();
  }

  onSubmit(id: number){
    if(this.roleForms[id].valid){
      const role: string = this.roleForms[id].value.roleSelection;
      this.updateAllUsers(this.userService.updateRole(id, role));
    }
  }
}

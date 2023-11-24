import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable, of } from 'rxjs';
import { IClientNotes } from 'src/app/client/data-access/types/client/client-details.interface';
import { NoteCardComponent } from '../note-card/note-card.component';

@Component({
  selector: 'note-list-card',
  standalone: true,
  imports: [CommonModule, NoteCardComponent],
  templateUrl: './note-list-card.component.html',
  styleUrls: ['./note-list-card.component.scss']
})
export class NoteListCardComponent {
  @Input() notes$: Observable<IClientNotes[]> = of([])
} 

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable, of } from 'rxjs';
import { NoteCardComponent } from '../note-card/note-card.component';
import { IClientNoteChange, IClientNotes } from 'src/app/client/data-access/types/client/client-note.interface';

@Component({
  selector: 'note-list-card',
  standalone: true,
  imports: [CommonModule, NoteCardComponent],
  templateUrl: './note-list-card.component.html',
  styleUrls: ['./note-list-card.component.scss']
})
export class NoteListCardComponent implements OnInit{
  @Input() notes$: Observable<IClientNotes[]> = of([])
  @Input() tripMap: Map<number, string> = new Map<number, string>()
  @Input() clientId: number = 0

  @Output() noteDelete = new EventEmitter<number>()
  @Output() noteChange = new EventEmitter<IClientNoteChange>()
 
  onNoteChange(changeNote: IClientNoteChange){ this.noteChange.emit(changeNote) }
  onNoteDelete(noteIndex: number){ this.noteDelete.emit(noteIndex) }

  constructor(){}

  ngOnInit(): void {
    console.log(this.clientId)
  }
} 

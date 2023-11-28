import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IClientNoteChange, IClientNotes } from '../../../data-access/types/client/client-note.interface';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'note-card',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.Default,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './note-card.component.html',
  styleUrls: ['./note-card.component.scss']
})
export class NoteCardComponent implements OnInit, OnDestroy{
  @Input() note!: IClientNotes | undefined
  @Input() tripMap!: Map<number,string>
  @Input() clientId !: number
  @Input() noteIndex !: number | undefined

  @Output() noteChange = new EventEmitter<IClientNoteChange>()
  @Output() noteDelete = new EventEmitter<number>()

  noteForm: FormGroup
  MAX_LENGTH: number = 2

  onChange(){
    console.log(this.noteIndex, this.noteForm.get('tripId')?.value)
    const changedNote: IClientNotes = this.noteForm.value
    changedNote.clientId = this.clientId
    const isNewNote = this.note === undefined

    if (this.noteForm.valid) {
      this.noteChange.emit({
        clientNote: changedNote,
        index: this.noteIndex,
        newNote: isNewNote 
      })

      if (isNewNote)
        this.noteForm.reset({ clientId: this.clientId, tripId: null });
    }
  }

  trackByFn(index: number, entry: [number, string]): number {return entry[0]}

  onDelete(){this.noteDelete.emit(this.noteIndex)}

  ngOnInit(): void {
    this.noteForm.patchValue({clientId: this.clientId})
    if (this.note){
      this.noteForm.patchValue({
        clientId: this.clientId,
        tripId: this.note.tripId,
        noteBody: this.note.noteBody,
        noteTitle: this.note.noteTitle
      })
    }
  }

  constructor(private fb: FormBuilder) {
    this.noteForm = this.fb.group({
      clientId: [0],
      tripId: [<number | null> null],
      noteBody: ['', Validators.required],
      noteTitle: ['', [Validators.required, Validators.minLength(this.MAX_LENGTH)]]
    });
  }

  ngOnDestroy(): void {
    console.log("Destroy:", this.noteIndex, this.note)
  }
}

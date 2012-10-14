package com.cloy.ottavino.sequencer;

import java.util.Arrays;

import com.cloy.ottavino.Chord;
import com.cloy.ottavino.Melody;
import com.cloy.ottavino.Note;
import com.cloy.ottavino.tone.ToneInstrument;

public class ToneSequence implements Sequence {
	
	private final Chord[] chords;
	private final ToneInstrument instrument;
	
	private boolean on = false;
	
	public ToneSequence(int panPosition, ToneInstrument instrument, Chord...chords) {
		this.chords = chords;
		this.instrument = instrument;
		System.out.println("Created " + this);
	}
	
	public ToneSequence(int pan, ToneInstrument instrument, Melody melody) {
		this(pan, instrument, getChords(melody));
	}
	
	private static Chord[] getChords(Melody melody) {
		Note[] notes = melody.getNotes();
		Chord[] chords = new Chord[notes.length];
		for(int i=0; i<notes.length; i++)
			chords[i] = new Chord(notes[i]);
		return chords;
	}
	
	@Override
	public void on(int position) {
		Chord chord = chords[position % chords.length];
		if(chord != null && !on) {
			start(chord);
			on = true;
		}
	}
	
	@Override
	public void off(int position) {
		Chord chord = chords[position % chords.length];
		Chord nextChord = chords[(position + 1) % chords.length];
		if(chord != null && !chord.equals(nextChord)) {
			stop(chord);
			on = false;
		}
	}
	
	private void start(Chord chord) {
		for(Note note : chord.getNotes())
			if(note != null)
				instrument.start(note.getFrequency());
	}
	
	private void stop(Chord chord) {
		for(Note note : chord.getNotes())
			if(note != null)
				instrument.stop(note.getFrequency());
	}
	
	public String toString() {
		return "Sequence-" + Arrays.toString(this.chords);
	}
}

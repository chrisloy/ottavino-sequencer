package com.cloy.ottavino.sequencer;

import java.util.Arrays;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.Synthesizer;

import com.cloy.ottavino.Chord;
import com.cloy.ottavino.Melody;
import com.cloy.ottavino.Note;
import com.cloy.ottavino.midi.MidiInstrument;

public class MidiSequence implements Sequence {
	
	private final MidiChannel channel;
	private final Chord[] chords;
	
	private boolean on = false;
	
	public MidiSequence(Synthesizer synth, int panPosition, MidiInstrument instrument, Chord...chords) {
		this.channel = instrument.getMidiChannel(synth);
		this.chords = chords;
		this.channel.controlChange(10, panPosition);
		System.out.println("Created " + this);
	}
	
	public MidiSequence(Synthesizer synth, int pan, MidiInstrument instrument, Melody melody) {
		this(synth, pan, instrument, getChords(melody));
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
			start(channel, chord);
			on = true;
		}
	}
	
	@Override
	public void off(int position) {
		Chord chord = chords[position % chords.length];
		Chord nextChord = chords[(position + 1) % chords.length];
		if(chord != null && !chord.equals(nextChord)) {
			stop(channel, chord);
			on = false;
		}
	}
	
	private void start(MidiChannel channel, Chord chord) {
		for(Note note : chord.getNotes())
			if(note != null)
				channel.noteOn(note.getNoteNumber(), note.getVelocity());
	}
	
	private void stop(MidiChannel channel, Chord chord) {
		for(Note note : chord.getNotes())
			if(note != null)
				channel.noteOff(note.getNoteNumber(), note.getDecay());
	}
	
	public String toString() {
		return "Sequence-" + Arrays.toString(this.chords);
	}
}

package com.cloy.ottavino.sequencer;

import javax.sound.midi.Synthesizer;

import com.cloy.ottavino.Chord;
import com.cloy.ottavino.Melody;
import com.cloy.ottavino.MidiInstrument;

public class MidiSequencer extends Sequencer<MidiInstrument> {
	
	private final Synthesizer synth;
	
	public MidiSequencer(int bpm, Synthesizer synth) {
		super(bpm);
		this.synth = synth;
	}
	
	public void add(MidiInstrument instrument, Chord...chords) {
		add(new MidiSequence(synth, 63, instrument, chords));
	}
	
	public void add(MidiInstrument instrument, Melody melody) {
		add(new MidiSequence(synth, 63, instrument, melody));
	}
	
	public void add(MidiInstrument instrument, int pan, Chord...chords) {
		add(new MidiSequence(synth, pan, instrument, chords));
	}
	
	public void add(MidiInstrument instrument, int pan, Melody melody) {
		add(new MidiSequence(synth, pan, instrument, melody));
	}

}

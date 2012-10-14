package com.cloy.ottavino.sequencer;

import com.cloy.ottavino.Chord;
import com.cloy.ottavino.Melody;
import com.cloy.ottavino.tone.ToneInstrument;

public class ToneSequencer extends Sequencer<ToneInstrument> {

	public ToneSequencer(int bpm) {
		super(bpm);
	}

	public void add(ToneInstrument instrument, Chord...chords) {
		add(new ToneSequence(63, instrument, chords));
	}
	
	public void add(ToneInstrument instrument, Melody melody) {
		add(new ToneSequence(63, instrument, melody));
	}
	
	public void add(ToneInstrument instrument, int pan, Chord...chords) {
		add(new ToneSequence(pan, instrument, chords));
	}
	
	public void add(ToneInstrument instrument, int pan, Melody melody) {
		add(new ToneSequence(pan, instrument, melody));
	}
}

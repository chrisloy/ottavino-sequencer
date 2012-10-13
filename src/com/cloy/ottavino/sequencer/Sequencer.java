package com.cloy.ottavino.sequencer;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Synthesizer;

import com.cloy.ottavino.Chord;
import com.cloy.ottavino.Instrument;
import com.cloy.ottavino.Melody;

public class Sequencer {
	
	private final Synthesizer synth;
	private final long interval;
	private final List<Sequence> sequences = new ArrayList<Sequence>();
	
	public Sequencer(int bpm, Synthesizer synth) {
		this.interval = 60000 / bpm;
		this.synth = synth;
	}
	
	public void play(int beats) {
		for(int i=0; i<beats; i++) {
			for(Sequence s : sequences) {
				s.on(i);
			}
			try {
				Thread.sleep(interval);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			for(Sequence s : sequences) {
				s.off(i);
			}
		}
		try {
			Thread.sleep(interval);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	public void add(Sequence s) {
		sequences.add(s);
	}
	
	public void add(Instrument instrument, Chord...chords) {
		add(new Sequence(synth, 63, instrument, chords));
	}
	
	public void add(Instrument instrument, Melody melody) {
		add(new Sequence(synth, 63, instrument, melody));
	}
	
	public void add(Instrument instrument, int pan, Chord...chords) {
		add(new Sequence(synth, pan, instrument, chords));
	}
	
	public void add(Instrument instrument, int pan, Melody melody) {
		add(new Sequence(synth, pan, instrument, melody));
	}
}

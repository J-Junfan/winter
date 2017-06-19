/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.winter.model.defaultmodel.comparators;


import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Record;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;

/**
 * {@link Comparator} for {@link Record}s based on their
 *  values, and their
 * {@link TokenizingJaccardSimilarity} similarity, with a lower casing
 * beforehand.
 * 
 * @author Alexander Brinkmann (albrinkm@mail.uni-mannheim.de)
 * 
 */
public class RecordComparatorLowerCaseJaccard extends RecordComparator {

	public RecordComparatorLowerCaseJaccard(Attribute attributeRecord1, Attribute attributeRecord2, double threshold, boolean squared) {
		super(attributeRecord1, attributeRecord2);
		this.threshold 	= threshold;
		this.squared	= squared;
	}

	private static final long serialVersionUID = 1L;
	private double threshold;
	private boolean squared;
	
	
	TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();

	@Override
	public double compare(Record record1, Record record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// preprocessing
			String s1 = record1.getValue(this.getAttributeRecord1());
			if (s1 != null) {
				s1 = s1.toLowerCase();
			} else {
				s1 = "";
			}
			String s2 = record2.getValue(this.getAttributeRecord2());
			if (s2 != null) {
				s2 = s2.toLowerCase();
			} else {
				s2 = "";
			}

			// calculate similarity
			double similarity = sim.calculate(s1, s2);

			// postprocessing
			if (similarity <= this.threshold) {
				similarity = 0;
			}
			if(squared)
				similarity *= similarity;

			return similarity;
	}

}

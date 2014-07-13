package com.yumidea.tool.annotation;

import java.io.*;
import java.util.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;

/**
 * This class is the processor that analyzes CaseID annotations.
 * 
 * @date 2014-05-12
 * @author Owen Tsao
 */

@SupportedAnnotationTypes({ "com.yumidea.tool.annotation.CaseIDs",
		"com.yumidea.tool.annotation.CaseID" })
public class CaseIDAnnotationProcessor extends AbstractProcessor {

	Set<String> CaseIDSet = new HashSet<String>();

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		for (TypeElement t : annotations) {
			for (Element e : roundEnv.getElementsAnnotatedWith(t)) {
				CaseIDs caseIDs = e.getAnnotation(CaseIDs.class);

				if (caseIDs != null) {
					for (CaseID tims : caseIDs.value()) {
						if (tims != null) {
							CaseIDSet.add(tims.id());
						}
					}
				}

				CaseID tims = e.getAnnotation(CaseID.class);
				if (tims != null) {
					CaseIDSet.add(tims.id());
				}
			}

		}
		try {
			writeReportFile(CaseIDSet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Write the statistic of case id in a report file.
	 * 
	 * @param timsID
	 *            a Set of case id
	 */
	private void writeReportFile(Set<String> timsID) throws IOException {

		FileWriter writer = new FileWriter("C:\\idsStatistic.txt");
		for (Iterator<String> it = timsID.iterator(); it.hasNext();) {
			String id = (String) it.next();
			if (id != null) {
				writer.write(id + ", ");
				writer.write(" ");
				writer.write(System.getProperty("line.separator"));
			}
		}
		writer.close();
	}
}
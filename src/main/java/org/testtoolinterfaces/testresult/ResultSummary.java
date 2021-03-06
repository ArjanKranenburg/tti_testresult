/**
 * 
 */
package org.testtoolinterfaces.testresult;

import org.testtoolinterfaces.testresult.TestResult.VERDICT;
import org.testtoolinterfaces.utils.Trace;

/**
 * @author Arjan Kranenburg
 *
 */
public class ResultSummary
{
	private int myNrOfTCsPassed;
	private int myNrOfTCsFailed;
	private int myNrOfTCsUnknown;
	private int myNrOfTCsError;
	
    /**
	 * @param aNrOfTCsPassed
	 * @param aNrOfTCsFailed
	 */
	public ResultSummary(int aNrOfTCsPassed, int aNrOfTCsFailed)
	{
		this( aNrOfTCsPassed, aNrOfTCsFailed, 0, 0 );
	}

	/**
	 * @param aNrOfTCsPassed
	 * @param aNrOfTCsFailed
	 * @param aNrOfTCsUnknown
	 * @param aNrOfTCsError
	 */
	public ResultSummary(int aNrOfTCsPassed, int aNrOfTCsFailed, int aNrOfTCsUnknown, int aNrOfTCsError)
	{
	    Trace.println( Trace.CONSTRUCTOR, "ResultSummary( " + aNrOfTCsPassed + ", "
	                  										+ aNrOfTCsFailed + ", "
	                  										+ aNrOfTCsUnknown + ", "
	                  										+ aNrOfTCsError + " )" );
	    
	    myNrOfTCsPassed = aNrOfTCsPassed;
	    myNrOfTCsFailed = aNrOfTCsFailed;
	    myNrOfTCsUnknown = aNrOfTCsUnknown;
	    myNrOfTCsError = aNrOfTCsError;
	}

	/**
	 * @return
	 */
	public int getNrOfTCs()
	{
	    Trace.println(Trace.GETTER);

	    return myNrOfTCsPassed + myNrOfTCsFailed + myNrOfTCsUnknown + myNrOfTCsError;
	}

	/**
	 * @return the number of passed test cases
	 */
	public int getNrOfTCsPassed()
	{
	    Trace.println(Trace.GETTER);
	    return myNrOfTCsPassed;
	}


	/**
	 * @return the number of failed test cases
	 */
	public int getNrOfTCsFailed()
	{
	    Trace.println(Trace.GETTER);
	    return myNrOfTCsFailed;
	}
	
	/**
	 * @return the number of passed test cases
	 */
	public int getNrOfTCsUnknown()
	{
	    Trace.println(Trace.GETTER);
	    return myNrOfTCsUnknown;
	}


	/**
	 * @return the number of failed test cases
	 */
	public int getNrOfTCsError()
	{
	    Trace.println(Trace.GETTER);
	    return myNrOfTCsError;
	}

	public void setResult(VERDICT aResult)
	{
		// NOP
	}
	
	/**
	 * @return VERDICT.UNKNOWN (Always)
	 * Note: There is no total verdict.
	 *       Use the getNrOf...() methods to get statistics of this Test Group.
	 */
	public VERDICT getResult()
	{
	    Trace.println(Trace.GETTER);
		return VERDICT.UNKNOWN;
	}

	/**
	 * @param verdict
	 */
	public void addVerdict(VERDICT verdict) {

		if (verdict == VERDICT.PASSED) {
			myNrOfTCsPassed += 1;
		} else if (verdict == VERDICT.FAILED) {
			myNrOfTCsFailed += 1;
		} else if (verdict == VERDICT.ERROR) {
			myNrOfTCsError += 1;
		} else {
			myNrOfTCsUnknown += 1;
		}
	}

	/**
	 * @param summary
	 * @return
	 */
	public void addSummary(ResultSummary summary) {
		myNrOfTCsPassed += summary.getNrOfTCsPassed();
		myNrOfTCsFailed += summary.getNrOfTCsFailed();
		myNrOfTCsError  += summary.getNrOfTCsError();
		myNrOfTCsUnknown  += summary.getNrOfTCsUnknown();
	}

	/**
	 * @param tgEntryResult
	 * @return
	 */
	public void addResult( TestGroupEntryResult tgEntryResult ) {
		if ( tgEntryResult instanceof TestGroupResult ) {
			ResultSummary tgSummary = ((TestGroupResult) tgEntryResult).getSummary();
			this.addSummary(tgSummary);
		}
		else if ( tgEntryResult instanceof TestCaseResult ) {
			VERDICT verdict = ((TestCaseResult) tgEntryResult).getResult();
			this.addVerdict(verdict);
		}
		else if ( tgEntryResult instanceof TestGroupResultLink ) {
			ResultSummary tgSummary = ((TestGroupResultLink) tgEntryResult).getSummary();
			this.addSummary(tgSummary);
		}
		else if ( tgEntryResult instanceof TestCaseResultLink ) {
			VERDICT verdict = ((TestCaseResultLink) tgEntryResult).getResult();
			this.addVerdict(verdict);
		}
		else if ( tgEntryResult instanceof TestExecItemIterationResult ) {
			ResultSummary tgSummary = ((TestExecItemIterationResult) tgEntryResult).getSummary();
			this.addSummary(tgSummary);
		}
		else {
			this.addVerdict( VERDICT.UNKNOWN );
		}
	}
}

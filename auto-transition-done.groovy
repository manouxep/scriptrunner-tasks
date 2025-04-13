import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.bc.issue.IssueService

// Get current user and services
def currentUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser
def groupManager = ComponentAccessor.groupManager
def issueService = ComponentAccessor.issueService

// Only run if user is in 'jira-administrators'
if (!groupManager.isUserInGroup(currentUser, "jira-administrators")) return

// ID of the transition to DONE (update if needed)
def transitionIdToDone = 11

// Function to perform transition
def doTransition = { Issue targetIssue ->
    def inputParams = issueService.newIssueInputParameters()
    inputParams.setSkipScreenCheck(true)

    def validation = issueService.validateTransition(
        currentUser,
        targetIssue.id,
        transitionIdToDone,
        inputParams
    )

    if (validation.isValid()) {
        def result = issueService.transition(currentUser, validation)
        if (result.isValid()) {
            log.info("Transition to DONE succeeded for issue: ${targetIssue.key}")
        } else {
            log.warn("Transition execution failed for issue: ${targetIssue.key}, errors: ${result.errorCollection}")
        }
    } else {
        log.warn("Transition validation failed for issue: ${targetIssue.key}, errors: ${validation.errorCollection}")
    }
}

// Apply to parent issue
doTransition(issue)

// Apply to all subtasks
issue.subTaskObjects.each { subtask ->
    doTransition(subtask)
}

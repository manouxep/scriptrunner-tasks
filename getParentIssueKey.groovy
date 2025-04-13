import com.onresolve.scriptrunner.runner.rest.common.CustomEndpointDelegate
import groovy.json.JsonBuilder
import groovy.transform.BaseScript
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.core.Response
import com.atlassian.jira.component.ComponentAccessor

@BaseScript CustomEndpointDelegate delegate
getParentIssueKey(httpMethod: "GET", groups: ["adaptavist-users"]) {
    MultivaluedMap queryParams, body, HttpServletRequest request ->

    String issueKey = queryParams.getFirst("issueKey")
    if (!issueKey) {
        return Response.status(400).entity("Missing 'issueKey' query parameter").build()
    }

    def issue = ComponentAccessor.issueManager.getIssueByCurrentKey(issueKey)
    if (!issue) {
        return Response.status(404).entity("Issue not found: $issueKey").build()
    }

    def parent = issue.getParentObject()
    if (!parent) {
        return Response.status(404).entity("This issue has no parent").build()
    }

    def parentKey = parent.key
    def json = new JsonBuilder([parentIssueKey: parentKey])
    return Response.ok(json.toPrettyString()).build()
}

# ScriptRunner Tasks – Technical Assessment

This repository contains solutions for two technical tasks involving ScriptRunner for Jira Data Center.

---

## Task 1.1 – Auto-Transition to DONE

**Goal:**
Automatically transition a parent issue and its subtasks to the `DONE` status when a user in the `jira-administrators` group transitions the issue to `IN REVIEW`.

**Setup:**
- Go to *Jira Administration > Workflows*
- Edit the workflow where this rule should apply
- Select the transition leading to `IN REVIEW`
- Under *Post Functions*, add a *ScriptRunner Custom Script*
- Paste the script from `auto-transition-done.groovy`

**Details:**
- Only users in the `jira-administrators` group can trigger the automatic transition
- All subtasks are transitioned along with the parent issue

---

## Task 1.2 – REST Endpoint to Retrieve Parent Issue Key

**Goal:**
Create a REST endpoint that accepts an `issueKey` parameter and returns the key of the parent issue if it exists.

**Setup:**
- Go to *ScriptRunner > Scripted REST Endpoints*
- Add a new item named `getParentIssueKey`
- Paste the script from `getParentIssueKey.groovy`

**Usage:**
Make a GET request to:
```
/rest/scriptrunner/latest/custom/getParentIssueKey?issueKey=SUBTASK-123
```

**Expected Response:**
```json
{
  "parentIssueKey": "PROJECT-1"
}
```

**Error Handling:**
- 400 if the query parameter is missing
- 404 if the issue doesn't exist or has no parent

---

## Compatibility
These scripts were tested with:
- Jira Software Data Center v9.12.0
- ScriptRunner for Jira Data Center v8.44.0

---

## Files
- `auto-transition-done.groovy` – Script for workflow post-function (Task 1.1)
- `getParentIssueKey.groovy` – Scripted REST endpoint (Task 1.2)

---

These scripts are designed to be clean, reliable, and ready for use or demonstration in a professional Jira environment.

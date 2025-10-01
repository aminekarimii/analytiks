# GitHub Actions Release Workflow Summary

## Overview

Implemented a comprehensive GitHub Actions workflow for automated publishing of Analytiks modules to Maven Central.

## What Was Created

### 1. Workflow File: `.github/workflows/release.yml`

**Location:** `.github/workflows/release.yml`

**Triggers:**
- **Manual (workflow_dispatch)**: Select specific modules to publish
- **Automatic (tags)**: Push tags matching `v*.*.*` to publish all modules

**Features:**
- ✅ Selective module publishing (single, core, addons, or all)
- ✅ Dry-run mode for testing without publishing
- ✅ Secure credential handling via GitHub Secrets
- ✅ Automatic build and test before publishing
- ✅ GitHub Release creation for tagged releases
- ✅ Comprehensive job summary with results

**Workflow Steps:**
1. Checkout code
2. Set up JDK 17
3. Decode GPG signing key from base64
4. Create local.properties with credentials
5. Build all modules
6. Run tests
7. Publish selected modules
8. Cleanup sensitive files
9. Create GitHub Release (for tags)
10. Generate summary report

### 2. Setup Documentation: `.github/RELEASE_SETUP.md`

**Location:** `.github/RELEASE_SETUP.md`

**Contents:**
- Complete setup instructions for GitHub Secrets
- GPG key generation guide
- Step-by-step secret configuration
- Troubleshooting common issues
- Security best practices
- Testing procedures

**Required Secrets:**
- `SIGNING_KEY_ID` - GPG key ID
- `SIGNING_PASSWORD` - GPG key passphrase
- `SIGNING_KEY_BASE64` - Base64-encoded GPG private key
- `CENTRAL_PORTAL_USERNAME` - Maven Central username
- `CENTRAL_PORTAL_PASSWORD` - Maven Central password
- `OSSRH_USERNAME` - OSSRH username (optional)
- `OSSRH_PASSWORD` - OSSRH password (optional)

### 3. Quick Reference: `.github/RELEASE_QUICK_REFERENCE.md`

**Location:** `.github/RELEASE_QUICK_REFERENCE.md`

**Contents:**
- Quick release commands
- Module name reference
- GitHub Secrets table
- Testing procedures
- Verification checklist
- Common issues and solutions
- Release workflow diagram

### 4. Updated Publishing Guide

**Location:** `PUBLISHING_GUIDE.md`

**Changes:**
- Added GitHub Actions as recommended publishing method
- Reorganized to show CI/CD first, local publishing second
- Added links to GitHub Actions documentation

## Usage Examples

### Example 1: Publish Single Module via GitHub Actions

1. Go to **Actions** tab in GitHub
2. Select **Release to Maven Central** workflow
3. Click **Run workflow**
4. Configure:
   - Branch: `main` or `feature/bom-implementation`
   - Module: `analytiks-appsflyer`
   - Dry run: `false`
5. Click **Run workflow**

### Example 2: Publish All Modules via Git Tag

```bash
# Update version
# Edit gradle.properties: PUBLISH_VERSION=1.2.0

# Commit and tag
git add gradle.properties
git commit -m "Bump version to 1.2.0"
git tag v1.2.0
git push origin v1.2.0

# GitHub Actions automatically publishes all modules
```

### Example 3: Dry Run Test

```bash
# Via GitHub Actions UI
1. Actions → Release to Maven Central
2. Module: analytiks-core
3. Dry run: ✅ checked
4. Run workflow
```

## Workflow Options

### Manual Workflow Dispatch

| Input | Type | Options | Description |
|-------|------|---------|-------------|
| `module` | choice | `all`, `core`, `all-addons`, or specific module | What to publish |
| `dry_run` | boolean | `true` / `false` | Test without publishing |

### Module Options

**Grouped:**
- `all` - Publish all modules (core + addons + BOM)
- `core` - Publish core modules (analytiks-core, analytiks, analytiks-bom)
- `all-addons` - Publish all addon modules

**Individual:**
- `analytiks-core`
- `analytiks`
- `analytiks-bom`
- `analytiks-appsflyer`
- `analytiks-addon-amplitude`
- `analytiks-addon-appvisor`
- `analytiks-addon-azureinsight`
- `analytiks-addon-googleanalytics`
- `analytiks-addon-mixpanel`
- `analytiks-addon-segment`
- `analytiks-addon-timber`

## Security Features

1. **GitHub Secrets**: All sensitive data stored securely
2. **Base64 Encoding**: GPG keys encoded for safe storage
3. **Automatic Cleanup**: Sensitive files removed after use
4. **No Hardcoded Credentials**: All credentials from secrets
5. **Secure Key Handling**: GPG key decoded only during workflow

## Benefits

### For Maintainers

1. **Automated Publishing**: No need for local setup
2. **Consistent Environment**: Same build environment every time
3. **Audit Trail**: All releases tracked in GitHub Actions
4. **Easy Rollback**: Can re-run workflows if needed
5. **Selective Publishing**: Publish only what changed

### For Contributors

1. **No Local Setup**: Don't need GPG keys or Maven credentials
2. **Transparent Process**: Can see exactly what happens
3. **Safe Testing**: Dry-run mode prevents accidental releases
4. **Clear Documentation**: Step-by-step guides available

## Testing Checklist

Before using in production:

- [ ] Set up all required GitHub Secrets
- [ ] Test dry-run mode with a single module
- [ ] Verify GPG key is properly configured
- [ ] Test manual workflow dispatch
- [ ] Test tag-based automatic release (on test branch)
- [ ] Verify artifacts appear on Maven Central
- [ ] Check signatures are valid
- [ ] Test BOM publishing
- [ ] Verify GitHub Release creation

## Next Steps

### 1. Set Up GitHub Secrets

Follow [.github/RELEASE_SETUP.md](.github/RELEASE_SETUP.md) to configure:
- GPG signing keys
- Maven Central credentials

### 2. Test Dry Run

```bash
# Via GitHub Actions
1. Actions → Release to Maven Central
2. Module: analytiks-core
3. Dry run: ✅ checked
4. Run workflow
```

### 3. Publish Test Version

```bash
# Update version to test version
# Edit gradle.properties: PUBLISH_VERSION=1.2.0-test

# Commit and push
git add gradle.properties
git commit -m "Test version"
git push

# Run workflow manually
# Actions → Release to Maven Central → Run workflow
```

### 4. Publish Production Release

```bash
# Update to production version
# Edit gradle.properties: PUBLISH_VERSION=1.2.0

# Commit, tag, and push
git add gradle.properties
git commit -m "Release version 1.2.0"
git tag v1.2.0
git push origin v1.2.0

# Workflow runs automatically
```

## Files Created/Modified

### Created
1. `.github/workflows/release.yml` - Main workflow file
2. `.github/RELEASE_SETUP.md` - Setup documentation
3. `.github/RELEASE_QUICK_REFERENCE.md` - Quick reference guide
4. `GITHUB_ACTIONS_SUMMARY.md` - This file

### Modified
1. `PUBLISHING_GUIDE.md` - Added GitHub Actions section

## Workflow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                  GitHub Actions Workflow                    │
└─────────────────────────────────────────────────────────────┘

Trigger: Manual or Tag Push
         │
         ├─> Checkout Code
         │
         ├─> Setup JDK 17
         │
         ├─> Decode GPG Key (base64 → file)
         │
         ├─> Create local.properties
         │   └─> Add signing credentials
         │   └─> Add Maven Central credentials
         │
         ├─> Build All Modules
         │   └─> ./gradlew build
         │
         ├─> Run Tests
         │   └─> ./gradlew test
         │
         ├─> Determine Module to Publish
         │   ├─> Manual: Use input parameter
         │   └─> Tag: Publish all
         │
         ├─> Publish Modules
         │   ├─> Core: analytiks-core, analytiks, analytiks-bom
         │   ├─> Addons: All addon modules
         │   └─> Single: Specific module
         │
         ├─> Cleanup
         │   └─> Remove signing.key.txt
         │   └─> Remove local.properties
         │
         ├─> Create GitHub Release (if tag)
         │
         └─> Generate Summary Report
```

## Support

For issues or questions:

1. Check [.github/RELEASE_SETUP.md](.github/RELEASE_SETUP.md)
2. Review [.github/RELEASE_QUICK_REFERENCE.md](.github/RELEASE_QUICK_REFERENCE.md)
3. See [PUBLISHING_GUIDE.md](PUBLISHING_GUIDE.md)
4. Check GitHub Actions logs
5. Review Maven Central validation reports

## Additional Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Maven Central Publishing](https://central.sonatype.org/publish/)
- [GPG Key Management](https://docs.github.com/en/authentication/managing-commit-signature-verification)
- [Gradle Signing Plugin](https://docs.gradle.org/current/userguide/signing_plugin.html)


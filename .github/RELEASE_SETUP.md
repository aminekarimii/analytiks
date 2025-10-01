# GitHub Actions Release Setup

This document explains how to set up GitHub Actions for automated releases to Maven Central.

## Overview

The `release.yml` workflow automates publishing Analytiks modules to Maven Central. It supports:

- **Manual releases** via workflow_dispatch (select specific modules)
- **Automatic releases** on version tags (publishes all modules)
- **Dry run mode** for testing without publishing
- **Selective publishing** (single module, core modules, all addons, or everything)

## Required GitHub Secrets

You need to configure the following secrets in your GitHub repository:

### Navigation
Go to: **Repository Settings → Secrets and variables → Actions → New repository secret**

### Secrets to Add

#### 1. Maven Central Credentials

**CENTRAL_PORTAL_USERNAME**
- Your Maven Central Portal username
- Get from: https://central.sonatype.com/

**CENTRAL_PORTAL_PASSWORD**
- Your Maven Central Portal password
- Get from: https://central.sonatype.com/

**OSSRH_USERNAME** (Optional - for backward compatibility)
- Your OSSRH username
- Get from: https://s01.oss.sonatype.org/

**OSSRH_PASSWORD** (Optional - for backward compatibility)
- Your OSSRH password
- Get from: https://s01.oss.sonatype.org/

#### 2. GPG Signing Credentials

**SIGNING_KEY_ID**
- Your GPG key ID (last 8 characters of your key)
- Example: `1234ABCD`
- Get it with: `gpg --list-secret-keys --keyid-format=short`

**SIGNING_PASSWORD**
- The passphrase for your GPG key
- This is what you entered when creating the key

**SIGNING_KEY_BASE64**
- Your GPG private key encoded in base64
- Generate it with:
  ```bash
  # Export your private key
  gpg --export-secret-keys YOUR_KEY_ID > signing.key
  
  # Encode to base64
  # On Linux/Mac:
  base64 signing.key > signing.key.base64
  
  # On Windows (PowerShell):
  [Convert]::ToBase64String([IO.File]::ReadAllBytes("signing.key")) > signing.key.base64
  
  # Copy the content of signing.key.base64 to the secret
  cat signing.key.base64
  
  # Clean up
  rm signing.key signing.key.base64
  ```

## Setting Up GPG Keys (If You Don't Have One)

### 1. Generate a GPG Key

```bash
gpg --gen-key
```

Follow the prompts:
- Use your real name
- Use your email (same as in gradle.properties)
- Set a strong passphrase

### 2. List Your Keys

```bash
gpg --list-secret-keys --keyid-format=short
```

Output example:
```
sec   rsa3072/1234ABCD 2024-01-01 [SC]
      ABCDEF1234567890ABCDEF1234567890ABCDEF12
uid           [ultimate] Your Name <your.email@example.com>
ssb   rsa3072/5678EFGH 2024-01-01 [E]
```

The key ID is `1234ABCD` (after the `/`)

### 3. Export Public Key to Key Servers

```bash
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
gpg --keyserver keys.openpgp.org --send-keys YOUR_KEY_ID
gpg --keyserver pgp.mit.edu --send-keys YOUR_KEY_ID
```

### 4. Export for GitHub Secret

```bash
# Export private key
gpg --export-secret-keys YOUR_KEY_ID > signing.key

# Encode to base64
base64 signing.key > signing.key.base64

# Copy the content
cat signing.key.base64

# Clean up
rm signing.key signing.key.base64
```

## Using the Workflow

### Method 1: Manual Release (Workflow Dispatch)

1. Go to **Actions** tab in GitHub
2. Select **Release to Maven Central** workflow
3. Click **Run workflow**
4. Select options:
   - **Module**: Choose what to publish
     - `all` - Publish everything
     - `core` - Publish core modules (analytiks-core, analytiks, analytiks-bom)
     - `all-addons` - Publish all addon modules
     - `analytiks-appsflyer` - Publish specific module
     - etc.
   - **Dry run**: Check to test without publishing
5. Click **Run workflow**

### Method 2: Automatic Release (Git Tags)

1. Update version in `gradle.properties`:
   ```properties
   PUBLISH_VERSION=1.2.0
   ```

2. Commit and push changes:
   ```bash
   git add gradle.properties
   git commit -m "Bump version to 1.2.0"
   git push
   ```

3. Create and push a tag:
   ```bash
   git tag v1.2.0
   git push origin v1.2.0
   ```

4. The workflow will automatically:
   - Build all modules
   - Run tests
   - Publish all modules to Maven Central
   - Create a GitHub Release

## Workflow Steps

The workflow performs the following steps:

1. **Checkout code** - Gets the repository code
2. **Set up JDK 17** - Configures Java environment
3. **Decode signing key** - Decodes the base64 GPG key
4. **Create local.properties** - Sets up credentials
5. **Build all modules** - Compiles everything
6. **Run tests** - Executes unit tests
7. **Publish modules** - Publishes to Maven Central
8. **Cleanup** - Removes sensitive files
9. **Create GitHub Release** - Creates release (for tags only)
10. **Summary** - Displays results

## Troubleshooting

### Issue: "Invalid signature"

**Solution:** Ensure your GPG key is properly exported and uploaded to key servers.

```bash
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
```

### Issue: "Unauthorized"

**Solution:** Check your Maven Central credentials:
- Verify username and password are correct
- Ensure you're using the new Central Portal credentials
- Check that secrets are properly set in GitHub

### Issue: "Component already exists"

**Solution:** You're trying to publish a version that already exists on Maven Central.
- Bump the version in `gradle.properties`
- Maven Central doesn't allow overwriting published versions

### Issue: "Build failed"

**Solution:** 
- Check the build logs in GitHub Actions
- Ensure all tests pass locally: `./gradlew test`
- Verify all dependencies are available

## Testing the Workflow

### Dry Run Test

1. Go to Actions → Release to Maven Central
2. Run workflow with:
   - Module: `analytiks-core`
   - Dry run: ✅ **checked**
3. Verify the workflow completes successfully
4. Check logs to ensure it says "Dry run: Would publish..."

### Actual Release Test

1. Create a test branch
2. Update version to something like `1.2.0-test`
3. Run workflow with:
   - Module: `analytiks-core`
   - Dry run: ❌ **unchecked**
4. Verify publication on Maven Central
5. Delete the test version if needed (contact Sonatype support)

## Security Best Practices

1. **Never commit secrets** to the repository
2. **Use GitHub Secrets** for all sensitive data
3. **Rotate credentials** periodically
4. **Limit access** to repository secrets
5. **Review workflow runs** regularly
6. **Use branch protection** for main/master branches

## Additional Resources

- [Maven Central Publishing Guide](https://central.sonatype.org/publish/publish-guide/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [GPG Key Generation](https://docs.github.com/en/authentication/managing-commit-signature-verification/generating-a-new-gpg-key)
- [Gradle Signing Plugin](https://docs.gradle.org/current/userguide/signing_plugin.html)

## Support

If you encounter issues:

1. Check the [PUBLISHING_GUIDE.md](../../PUBLISHING_GUIDE.md)
2. Review GitHub Actions logs
3. Check Maven Central validation reports
4. Contact Sonatype support for Maven Central issues


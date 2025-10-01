# Release Quick Reference

## 🚀 Quick Release Commands

### Publish All Modules (Recommended for new versions)

```bash
# 1. Update version
# Edit gradle.properties: PUBLISH_VERSION=1.2.0

# 2. Commit changes
git add gradle.properties
git commit -m "Bump version to 1.2.0"
git push

# 3. Create and push tag
git tag v1.2.0
git push origin v1.2.0

# GitHub Actions will automatically publish all modules
```

### Publish Single Module via GitHub Actions

1. Go to: **Actions** → **Release to Maven Central**
2. Click: **Run workflow**
3. Select module (e.g., `analytiks-appsflyer`)
4. Uncheck "Dry run"
5. Click: **Run workflow**

### Publish Locally (Manual)

```bash
# Single module
./gradlew publishModule -PmoduleName=analytiks-appsflyer

# Core modules
./gradlew publishCore

# All addons
./gradlew publishAllAddons

# Everything
./gradlew publishAll
```

## 📋 Module Names

### Core Modules
- `analytiks-core`
- `analytiks`
- `analytiks-bom`

### Addon Modules
- `analytiks-appsflyer`
- `analytiks-addon-amplitude`
- `analytiks-addon-appvisor`
- `analytiks-addon-azureinsight`
- `analytiks-addon-googleanalytics`
- `analytiks-addon-mixpanel`
- `analytiks-addon-segment`
- `analytiks-addon-timber`

## 🔑 Required GitHub Secrets

| Secret Name | Description | Example |
|-------------|-------------|---------|
| `SIGNING_KEY_ID` | GPG key ID (last 8 chars) | `1234ABCD` |
| `SIGNING_PASSWORD` | GPG key passphrase | `your-passphrase` |
| `SIGNING_KEY_BASE64` | Base64 encoded GPG private key | `LS0tLS1CRUdJTi...` |
| `CENTRAL_PORTAL_USERNAME` | Maven Central username | `your-username` |
| `CENTRAL_PORTAL_PASSWORD` | Maven Central password | `your-password` |

## 🧪 Testing Before Release

### Dry Run Test

```bash
# Via GitHub Actions
1. Actions → Release to Maven Central
2. Select module
3. ✅ Check "Dry run"
4. Run workflow

# Via Local
./gradlew :analytiks-core:build
./gradlew :analytiks-core:generatePomFileForReleasePublication
# Check: build/publications/release/pom-default.xml
```

### Build and Test Locally

```bash
# Build all
./gradlew build

# Run tests
./gradlew test

# Generate POM for verification
./gradlew :analytiks-bom:generatePomFileForReleasePublication
```

## 📦 Workflow Options

### Manual Workflow Dispatch

| Option | Values | Description |
|--------|--------|-------------|
| **Module** | `all`, `core`, `all-addons`, or specific module | What to publish |
| **Dry run** | `true` / `false` | Test without publishing |

### Automatic Tag-based Release

- Push tag matching `v*.*.*` (e.g., `v1.2.0`)
- Automatically publishes all modules
- Creates GitHub Release

## ✅ Verification Checklist

After publishing:

- [ ] Check Maven Central: https://central.sonatype.com/search?q=io.github.aminekarimii
- [ ] Verify all artifacts are present (AAR, POM, sources, javadoc)
- [ ] Check signatures are valid
- [ ] Test in a sample project
- [ ] Update release notes
- [ ] Announce release

## 🐛 Common Issues

### "Component already exists"
**Solution:** Version already published. Bump version in `gradle.properties`

### "Invalid signature"
**Solution:** Re-upload GPG key to key servers:
```bash
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
```

### "Unauthorized"
**Solution:** Check Maven Central credentials in GitHub Secrets

### "Build failed"
**Solution:** Run tests locally first:
```bash
./gradlew clean build test
```

## 📚 Documentation Links

- [Full Release Setup Guide](.github/RELEASE_SETUP.md)
- [Publishing Guide](../PUBLISHING_GUIDE.md)
- [BOM Documentation](../analytiks-bom/README.md)
- [Maven Central Portal](https://central.sonatype.com/)

## 🔄 Release Workflow

```
┌─────────────────────────────────────────────────────────────┐
│                    Release Process                          │
└─────────────────────────────────────────────────────────────┘

1. Update Version
   └─> Edit gradle.properties

2. Commit & Tag
   └─> git commit && git tag v1.2.0

3. Push Tag
   └─> git push origin v1.2.0

4. GitHub Actions
   ├─> Build all modules
   ├─> Run tests
   ├─> Sign artifacts
   ├─> Publish to Maven Central
   └─> Create GitHub Release

5. Verify
   └─> Check Maven Central
```

## 💡 Pro Tips

1. **Always test locally first**: `./gradlew build test`
2. **Use dry run** for first-time releases
3. **Publish BOM last** after all modules are published
4. **Tag format matters**: Use `v1.2.0` not `1.2.0`
5. **Check validation**: Maven Central validates all artifacts
6. **Be patient**: Maven Central sync can take 15-30 minutes

## 🆘 Need Help?

1. Check [RELEASE_SETUP.md](.github/RELEASE_SETUP.md)
2. Review GitHub Actions logs
3. Check Maven Central validation report
4. See [PUBLISHING_GUIDE.md](../PUBLISHING_GUIDE.md)



#!/usr/bin/env bash
#
#

set -euo pipefail

# These are only exported if they are non-empty.
FL_VERSION_NAME_VERSION_NAME=$(buildkite-agent meta-data get "version-name" --default "")
FL_VERSION_CODE_VERSION_CODE=$(buildkite-agent meta-data get "version-code" --default "")
FL_BUMP_TYPE_BUMP_TYPE=$(buildkite-agent meta-data get "bump-type" --default "")

if [ -n "$FL_VERSION_NAME_VERSION_NAME" ]; then
	export FL_VERSION_NAME_VERSION_NAME
fi

if [ -n "$FL_VERSION_CODE_VERSION_CODE" ]; then
	export FL_VERSION_CODE_VERSION_CODE
fi

if [ -n "$FL_BUMP_TYPE_BUMP_TYPE" ]; then
	export FL_BUMP_TYPE_BUMP_TYPE
fi

#Test the release build
bundle exec fastlane test_release

# Prep and commit the release's version and build number. If an explicit version name isn't provided, use the
# bump_build lane.
if [[ -z "${FL_VERSION_NAME_VERSION_NAME:-}" ]]; then
    bundle exec fastlane prep_release_bump_build
else
    bundle exec fastlane prep_release_bump_version
fi

#Sign APK & firebase app distribution
#bundle exec fastlane release

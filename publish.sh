#!/bin/bash

# Read the version from the podspec file
VERSION=$(sed -n "s/.*s.version *= *'\(.*\)'/\1/p" SwiftAudioPro.podspec)

# Check if the version was found
if [ -z "$VERSION" ]; then
  echo "Version not found in SwiftAudioPro.podspec"
  exit 1
fi

echo "Version found: $VERSION"

# Create a git tag
if ! git tag $VERSION; then
  echo "Failed to create git tag"
  exit 1
fi

# Push the tag to the remote repository
if ! git push origin $VERSION; then
  echo "Failed to push git tag to remote repository"
  exit 1
fi

# Publish the pod to CocoaPods
if ! pod trunk push SwiftAudioPro.podspec; then
  echo "Failed to publish pod to CocoaPods"
  exit 1
fi

echo "Pod successfully published"

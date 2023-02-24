Layer for hypercar demo

Dependencies
============
meta-openembedded
meta-virtualization
meta-arm


Patches
=======

Maintainer: Pavel Zhukov <pazhukov@suse.de>

Build
=====

install repo tool from your distribution
```
zypper install git-repo
```

Clone repos and build the image
```
mkdir hypercar && cd hypercar
repo init -u https://gitlab.suse.de/pazhukov/meta-hypercar.git
repo sync --no-clone-bundle
TEMPLATECONF=../meta-hypercar/conf/templates/hypercar . core/oe-init-build-env
bitbake hypercar-image-demo
```


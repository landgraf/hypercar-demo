require recipes-core/images/core-image-base.bb

inherit uboot-xen-config

SUMMARY = "Dom0 image for hypercar demo"
IMAGE_INSTALL:append = " haveged "
PACKAGE_EXCLUDE:append = "rng-tools"

IMAGE_INSTALL:append = " xen"

EXTRA_IMAGE_FEATURES:append = " ssh-server-dropbear"
EXTRA_IMAGE_FEATURES:remove = " package-management"

PACKAGECONFIG:pn-networkmanager:remove =" ${@bb.utils.contains('MACHINE_FEATURES', 'wifi' ' ifupdown dnsmasq ', '', d}}"
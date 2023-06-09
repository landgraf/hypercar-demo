require recipes-core/images/core-image-base.bb

SUMMARY = "Dom0 image for hypercar demo"
IMAGE_INSTALL:append = " haveged "
PACKAGE_EXCLUDE:append = "rng-tools"

IMAGE_INSTALL:append = " xen"

EXTRA_IMAGE_FEATURES:append = " ssh-server-dropbear"
EXTRA_IMAGE_FEATURES:remove = " package-management"

PACKAGECONFIG:pn-networkmanager:remove =" ${@bb.utils.contains('MACHINE_FEATURES', 'wifi' ' ifupdown dnsmasq ', '', d}}"
COMPATIBLE_MACHINE = "(^qemuarm64.*|raspberrypi4-64)"
do_image_wic[depends] += "uboot-xen-config:do_deploy"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux/:"
SRC_URI:append = "                         \
	       	   file://systemd.cfg      \
		   file://timers.cfg       \
		   file://dmiid.cfg        \
		   file://xen-hypercar.cfg \
		 "

## disablebt should be replaced with proper disabling using devicetree/overlay
SRC_URI:append:raspberrypi4-64 = " file://rpi-hypercar.cfg "

KBUILD_DEFCONFIG:raspberrypi4-64="defconfig"

COMPATIBLE_MACHINE:qemuarm64-xen-uboot =  "^qemuarm64-xen-uboot$"

KERNEL_FEATURES:append = " \
    ${@bb.utils.contains('DISTRO_FEATURES', \
                         'xen', \
                         'features/xen-shmem.scc', \
                         '', d)} \
"

FILES:${KERNEL_PACKAGE_NAME}:append = " /boot/*.dtb"
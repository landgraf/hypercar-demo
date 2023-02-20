SUMMARY = "ImageBuilder, an Open Source collection of scripts to generate a U-Boot script for Xen hypervisor"

DESCRIPTION = "Building an embedded virtualized system with anything more than one Domain can be difficult, error prone and time consuming."\
"ImageBuilder, an Open Source collection of scripts (contributions encouraged), changes all that."\
"ImageBuilder generates a U-Boot script that can be used to load all of the binaries automatically and boot the full system fast. Given a collection of binaries such as Xen, Dom0 and a number of Dom0-less DomUs, ImageBuilder takes care of calculating all loading addresses, editing device tree with the necessary information, and even pre-configuring a disk image with kernels and rootfses."\
"ImageBuilder has been tested on Xilinx ZynqMP MPSoC boards. An up-to-date wikipage is also available at wiki.xenproject.org."

LICENSE="Apache-2.0"
SRCREV = "7760210b3c0e4ac29b7a7e5e2daba86f34ca79c2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d2794c0df5b907fdace235a619d80314"
SRC_URI = "git://github.com/Xilinx/imagebuilder.git;protocol=https;branch=master"

inherit native

RDEPENDS:${PN} = "u-boot-mkimage"
S="${WORKDIR}/git"
do_compile[noexec] = "1"
do_configure[noexec] = "1"


addtask do_deploy after do_compile before do_build

do_install(){
	install -d -m 0755 ${D}/usr/libexec/imagebuilder
	install -d -m 0755 ${D}/usr/libexec/imagebuilder/scripts/
	install -m 0755 scripts/uboot-script-gen  ${D}/usr/libexec/imagebuilder/scripts/
	install -m 0755 scripts/disk_image  ${D}/usr/libexec/imagebuilder/scripts/
}
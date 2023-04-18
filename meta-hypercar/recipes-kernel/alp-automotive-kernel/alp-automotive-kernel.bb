inherit bin_package

## TODO: proper osc/ibs fetcher is needed here

## SRC_URI = "ibs://<projectname>/<package_name>" ?
SRC_URI = "https://download.opensuse.org/repositories/home:/pazhukov:/hypercar-kernel:/hypercar-kernel-sources/ARM/aarch64/kernel-default-6.2.8-9.1.g99f564f.aarch64.rpm;subdir=${BP}"
LICENSE="GPL-2.0-only"
SRC_URI[sha256sum] = "9f702630753ed33eade59bfa9293b231d288435b055352c5fd45a6294eba5700"
DEPENDS = "bzip2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

IBS_VERSION = "6.2.8-9.g99f564f-default"
PROVIDES = "virtual/kernel"


inherit deploy

## part below has been copyied over from linux-dummy recipe
PACKAGES_DYNAMIC += "^kernel-module-.*"
PACKAGES_DYNAMIC += "^kernel-image-.*"
PACKAGES_DYNAMIC += "^kernel-firmware-.*"

PACKAGES += "kernel-modules kernel-vmlinux"
FILES:kernel-modules = ""
ALLOW_EMPTY:kernel-modules = "1"
DESCRIPTION:kernel-modules = "Kernel modules meta package"
FILES:kernel-vmlinux = ""
ALLOW_EMPTY:kernel-vmlinux = "1"
DESCRIPTION:kernel-vmlinux = "Kernel vmlinux meta package"

INHIBIT_DEFAULT_DEPS = "1"

COMPATIBLE_HOST = ".*-linux"

do_configure() {
	:
}

do_compile () {
	:
}

do_compile_kernelmodules() {
    :
}

do_shared_workdir () {
	:
}

do_install() {
	install -D -m 0755 ${S}/usr/lib/modules/${IBS_VERSION}/Image ${D}/boot/Image
	:
}

do_bundle_initramfs() {
	:
}

do_deploy() {
	install -D -m 0755 ${D}/boot/Image  ${DEPLOYDIR}/Image	
	:
}

addtask bundle_initramfs after do_install before do_deploy
addtask deploy after do_install
addtask shared_workdir after do_compile before do_install
addtask compile_kernelmodules

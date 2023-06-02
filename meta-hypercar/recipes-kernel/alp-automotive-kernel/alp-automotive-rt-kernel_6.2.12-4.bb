inherit bin_package

## TODO: proper osc/ibs fetcher is needed here

## SRC_URI = "ibs://<projectname>/<package_name>" ?
IBS_RELEASE = "1"
IBS_HASH = "g27a331a"
SRC_URI = "https://download.opensuse.org/repositories/home:/pazhukov:/hypercar-kernel:/hypercar-kernel-sources-rt/ARM/aarch64/kernel-rt-${PV}.${IBS_RELEASE}.${IBS_HASH}.aarch64.rpm;subdir=${BP}"
LICENSE="GPL-2.0-only"
SRC_URI[sha256sum] = "c0f9034e934695433f6a962af7af648ee74ee595a9fd5436797f6f4e15078d15"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"


inherit deploy

do_install() {
	cp -r ${S}/* ${D}/
	install -D -m 0755 ${S}/usr/lib/modules/${PV}.${IBS_HASH}-rt/Image ${D}/boot/Image-alprt
	:
}

do_deploy() {
	install -D -m 0755 ${D}/boot/Image-alprt  ${DEPLOYDIR}/Image-alprt
	:
}

pkg_postinst_ontarget:${PN}(){
      depmod -a
      grep -q control_d /proc/xen/capabilities && reboot || echo softdog > /etc/modules-load.d/softdog.conf
}

addtask do_deploy after do_install before do_build

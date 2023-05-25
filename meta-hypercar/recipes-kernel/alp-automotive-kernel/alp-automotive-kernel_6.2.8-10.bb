inherit bin_package

## TODO: proper osc/ibs fetcher is needed here

## SRC_URI = "ibs://<projectname>/<package_name>" ?
IBS_RELEASE = "1"
IBS_HASH = "g1727ac5"
SRC_URI = "https://download.opensuse.org/repositories/home:/pazhukov:/hypercar-kernel:/hypercar-kernel-sources/ARM/aarch64/kernel-default-${PV}.${IBS_RELEASE}.${IBS_HASH}.aarch64.rpm;subdir=${BP}"
LICENSE="GPL-2.0-only"
SRC_URI[sha256sum] = "f4d1ad6585d883ebcd82d56aa1058a291261eb8b7d8ca4dce949eb4f0e7da369"
DEPENDS = "bzip2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"


inherit deploy

do_install() {
	cp -r ${S}/* ${D}/
	install -D -m 0755 ${S}/usr/lib/modules/${PV}.${IBS_HASH}-default/Image ${D}/boot/Image
	:
}

do_deploy() {
	install -D -m 0755 ${D}/boot/Image  ${DEPLOYDIR}/Image-alp
	:
}

pkg_postinst_ontarget:${PN}(){
      depmod -a
      grep -q control_d /proc/xen/capabilities && reboot || echo softdog > /etc/modules-load.d/softdog.conf
}

addtask do_deploy after do_install before do_build

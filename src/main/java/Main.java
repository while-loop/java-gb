import helpers.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * This class serves as the entry point for executing the emulator. It also takes care of
 * commandline arguments
 */
public class Main {

    public static boolean skipBootrom;

    public static void main(String[] args) {
        Logger log = new Logger("Main");

        // process commandline arguments
        List<String> argsList = Arrays.asList(args);
        skipBootrom = argsList.contains("-sb") || argsList.contains("--skip-bootrom");
        boolean cartParseOnly = argsList.contains("-cpo") || argsList.contains("--cart-parse-only");
        boolean noAcceleration = argsList.contains("-noaccel") || argsList.contains("--no-hw-acceleration");

        if (argsList.contains("-h") || argsList.contains("-help") || argsList.contains("--help")) {
            System.out.println("USAGE: java Main [options]");
            System.out.println("\t -sb      (--skip-bootrom) \t\t\tBegin executing the game immediately, bypassing the Nintendo logo scroll.");
            System.out.println("\t -noaccel (--no-hw-acceleration) \tDisables the use of Java2D OpenGL.");
            return;
        }

        // log command line argument values:
        log.info("======= CMDLINE PARAMS =======");
        log.info("Skip Bootrom   set to " + skipBootrom);
        log.info("CartParseOnly  set to " + cartParseOnly);
        log.info("noAcceleration set to " + noAcceleration);
        log.info("==============================");


        // cpu_instrs
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/cpu_instrs/cpu_instrs.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/cpu_instrs/individual/01-special.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/cpu_instrs/individual/02-interrupts.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/cpu_instrs/individual/03-op sp,hl.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/cpu_instrs/individual/04-op r,imm.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/cpu_instrs/individual/05-op rp.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/cpu_instrs/individual/06-ld r,r.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/cpu_instrs/individual/07-jr,jp,call,ret,rst.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/cpu_instrs/individual/09-op r,r.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/cpu_instrs/individual/10-bit ops.gb", true);

        // instr_timing
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/instr_timing/instr_timing.gb", true);

        // interrupt_time
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/interrupt_time/interrupt_time.gb", true);

        // mem_timing
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/mem_timing/individual/01-read_timing.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/mem_timing/individual/02-write_timing.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/mem_timing/mem_timing.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/gb-test-roms/mem_timing-2/mem_timing.gb", true);



        // mooneye
        //Cartridge cart = new Cartridge("src/test/resources/mooneye-gb-test-roms/tests/acceptance/bits/reg_f.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/mooneye-gb-test-roms/tests/acceptance/bits/unused_hwio-GS.gb", true);

        //Cartridge cart = new Cartridge("src/test/resources/mooneye-gb-test-roms/tests/acceptance/interrupts/ie_push.gb", true);

        //Cartridge cart = new Cartridge("src/test/resources/mooneye-gb-test-roms/tests/acceptance/timer/div_write.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/mooneye-gb-test-roms/tests/acceptance/timer/rapid_toggle.gb", true);
        //Cartridge cart = new Cartridge("src/test/resources/mooneye-gb-test-roms/tests/acceptance/timer/tim00.gb", true);


        //Cartridge cart = new Cartridge("src/main/resources/tetris.gb", true);
        //Cartridge cart = new Cartridge("src/main/resources/drmario.gb", true);
        //Cartridge cart = new Cartridge("src/main/resources/10-print.gb", true);
        //Cartridge cart = new Cartridge("src/main/resources/pokebluejp.gb", true);
        Cartridge cart = new Cartridge("src/main/resources/supermarioland.gb", true);


        if (cartParseOnly) {
            return;
        }

        if (!noAcceleration) {
            System.setProperty("sun.java2d.opengl", "true");
        }
        log.info("\"sun.java2d.opengl\" set to " + System.getProperty("sun.java2d.opengl"));

        MbcManager mbc = new MbcManager(cart);
        Gpu gpu = new Gpu();
        MemoryManager mmu = new MemoryManager(mbc, gpu);
        Cpu cpu = new Cpu(mmu, gpu);

        // go
        cpu.main();
    }
}


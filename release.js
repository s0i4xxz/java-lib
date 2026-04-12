const { execSync } = require('child_process');

function run(cmd) {
    console.log(`> ${cmd}`);
    return execSync(cmd, { encoding: 'utf-8', stdio: ['pipe', 'pipe', 'pipe'] }).trim();
}

// Get latest tag
const tags = run('git tag --sort=-v:refname');
const latest = tags.split('\n')[0];

if (!latest || !latest.match(/^v\d+\.\d+\.\d+$/)) {
    console.error('No valid semver tag found. Latest:', latest);
    process.exit(1);
}

// Bump patch version
const [major, minor, patch] = latest.replace('v', '').split('.').map(Number);
const newTag = `v${major}.${minor}.${patch + 1}`;

console.log(`Current: ${latest} -> New: ${newTag}`);

// Stage, commit, tag, push
run('git add -A');

try {
    run('git commit -m "release ' + newTag + '"');
} catch (e) {
    if (e.stderr && e.stderr.includes('nothing to commit')) {
        console.log('No changes to commit, just creating tag.');
    } else {
        throw e;
    }
}

run(`git tag ${newTag}`);
run('git push origin main');
run(`git push origin ${newTag}`);

console.log(`\nDone! Published ${newTag}`);
console.log(`JitPack: https://jitpack.io/#s0i4xxz/java-lib/${newTag}`);

#version 150

#define PI 3.14159265358979323846

in vec2 texCoord;

uniform vec2 center;
uniform float radius;
uniform float brightness;

out vec4 fragColor;

float fmod(float x, float y) {
    return x - y * floor(x / y);
}

vec3 hsb2rgb(in vec3 c) {
    vec3 rgb = clamp(abs(mod(c.x * 6.0 + vec3(0.0, 4.0, 2.0), 6.0) - 3.0) - 1.0, 0.0, 1.0);
    return c.z * mix(vec3(1.0), rgb, c.y);
}

void main() {
    // Calculate the distance of the pixel from the center of the circle
    float dist = distance(texCoord, center);

    // If the pixel is outside the circle, set the color to transparent
    if (dist > radius)
    {
        fragColor = vec4(0.0, 0.0, 0.0, 0.0);
        return;
    }

    // Calculate the angle of the pixel relative to the center of the circle
    float angle = atan(center.y - texCoord.y, center.x - texCoord.x);

    // Normalize the angle to the range [0, 1]
    float hue = (angle + PI) / (PI * 2);

    // Map the distance to the saturation value
    float saturation = dist / radius;

    // Calculate the RGB color for the hue and saturation
    vec3 rgb = hsb2rgb(vec3(hue, saturation, brightness));

    // Set the output color to the calculated RGB color
    fragColor = vec4(rgb, 1.0);
}
